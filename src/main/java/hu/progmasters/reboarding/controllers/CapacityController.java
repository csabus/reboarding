package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.excpetion.CapacityNotSetException;
import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.models.Capacity;
import hu.progmasters.reboarding.models.User;
import hu.progmasters.reboarding.repositories.CapacityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/capacity")
public class CapacityController {
    @Autowired
    private CapacityRepository capacityRepository;

    /**
     * Gets all registered capacities representing the number of employees allowed in the office at the same time at a given day.
     * @return A {@link List} containing all {@link Capacity} object representations of all registered daily capacities.
     */
    @GetMapping
    public List<Capacity> list() {
        return capacityRepository.findAll();
    }
    /**
     * Gets the {@link Capacity} specified by this <code>date<code/>.
     * @param date the date on which we need to know the number of employees allowed to be at the office at the same time.
     * @return The <code>Capacity</code> object specified by the <code>date</code>
     * @throws CapacityNotSetException if no capacity corresponds to given <code>date</code> in the database
     */
    @GetMapping()
    @RequestMapping("{date}")
    public Capacity get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return capacityRepository.findByDate(date).orElseThrow(() -> new CapacityNotSetException(date));
    }
    /**
     * Creates a new {@link Capacity} object and the corresponding record in the database
     * @param capacity The {@code Capacity} object representing the number of employees allowed to be at the office at the same time at given day.
     * @return The created {@code Capacity}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Capacity create(@RequestBody final Capacity capacity) {
        long c = Math.max(capacity.getDailyCapacity(), CapacityRepository.MIN_CAPACITY);
        c = Math.min(c, CapacityRepository.MAX_CAPACITY);
        capacity.setDailyCapacity(c);
        return capacityRepository.saveAndFlush(capacity);
    }

    /**
     * Overwrites a previously set {@link Capacity}
     * @param date the date on which the {@code Capacity} is to be changed
     * @param capacity the new {@code Capacity} to be set
     * @return the updated {@code Capacity}
     * @throws CapacityNotSetException if no {@code Capacity} is set for the given day
     */
    @RequestMapping(value = "{date}", method = RequestMethod.PUT)
    public Capacity update(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           @RequestBody Capacity capacity) {
        Optional<Capacity> existingCapacity = capacityRepository.findByDate(date);

        if (existingCapacity.isPresent()) {
            long c = Math.max(capacity.getDailyCapacity(), CapacityRepository.MIN_CAPACITY);
            c = Math.min(c, CapacityRepository.MAX_CAPACITY);
            capacity.setDailyCapacity(c);

            BeanUtils.copyProperties(capacity, existingCapacity.get(), "date");
            return capacityRepository.saveAndFlush(existingCapacity.get());
        } else {
            throw new CapacityNotSetException(date);
        }
    }


}
