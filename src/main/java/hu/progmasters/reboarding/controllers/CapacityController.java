package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.excpetion.CapacityNotSetException;
import hu.progmasters.reboarding.models.Capacity;
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

    @GetMapping
    public List<Capacity> list() {
        return capacityRepository.findAll();
    }

    @GetMapping()
    @RequestMapping("{date}")
    public Capacity get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return capacityRepository.findByDate(date).orElseThrow(() -> new CapacityNotSetException(date));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Capacity create(@RequestBody final Capacity capacity) {
        long c = Math.max(capacity.getDailyCapacity(), CapacityRepository.MIN_CAPACITY);
        c = Math.min(c, CapacityRepository.MAX_CAPACITY);
        capacity.setDailyCapacity(c);
        return capacityRepository.saveAndFlush(capacity);
    }

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
