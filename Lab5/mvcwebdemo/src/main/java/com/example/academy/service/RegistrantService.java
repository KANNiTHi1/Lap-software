package com.example.academy.service;

import com.example.academy.model.Registrant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RegistrantService {
    private final List<Registrant> registrants = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGen = new AtomicLong(1);

    public List<Registrant> findAll() {
        return new ArrayList<>(registrants);
    }

    public Registrant add(Registrant r) {
        r.setId(idGen.getAndIncrement());
        registrants.add(r);
        return r;
    }
}