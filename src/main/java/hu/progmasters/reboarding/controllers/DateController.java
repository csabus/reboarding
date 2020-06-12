package hu.progmasters.reboarding.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/date")
public class DateController {

    @GetMapping
    @RequestMapping("{date}")
    public List<String> list(@PathVariable("date")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                     LocalDate date) {
        System.out.println(date);
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        return list;
    }
}
