package com.asimio.demo.rest;

import com.asimio.demo.config.dvdrental.DvdRentalTenantContext;
import com.asimio.dvdrental.dao.ActorDao;
import com.asimio.dvdrental.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@RequestMapping(value = "/demo")
@Transactional
public class DemoResource {

    @Autowired
    private ActorDao actorDao;

    @GetMapping(value = "/{db}/{id}")
    public String getActor(@PathVariable("db") String db, @PathVariable("id") String id) {
        DvdRentalTenantContext.setTenantId(db);
        Actor actor = this.actorDao.getOne(Integer.valueOf(id));

        return String.format("[actor: %s %s], [DemoResource instance: %s], [ActorDao instance: %s]",
                actor.getFirstName(), actor.getLastName(), this, this.actorDao);
    }

    @PatchMapping(value = "/{id}")
    public String updateActor(@PathVariable("id") String id, @RequestBody Map<String, Object> updates) {
        Actor actor = this.actorDao.getOne(Integer.valueOf(id));
        actor.setFirstName((String) updates.get("firstName"));
        return String.format("[actor: %s %s], [DemoResource instance: %s], [ActorDao instance: %s]",
                actor.getFirstName(), actor.getLastName(), this, this.actorDao);
    }
}