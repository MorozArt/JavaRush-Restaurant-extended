package com.moroz.restaurant.dao;

import com.moroz.restaurant.domain.kitchen.Cook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CookDao {

    public List<Cook> getCooks() {
        List<Cook> cooks = new ArrayList<>();

        Cook amigoCook = new Cook("Amigo Chief");
        cooks.add(amigoCook);

        Cook notAmigoCook = new Cook("NotAmigo Chief");
        cooks.add(notAmigoCook);

        return cooks;
    }
}
