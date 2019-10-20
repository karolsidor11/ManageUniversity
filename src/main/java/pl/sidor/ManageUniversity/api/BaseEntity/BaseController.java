package pl.sidor.ManageUniversity.api.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BaseController {

    private BaseService service;

    @Autowired
    public BaseController(BaseService service) {
        this.service = service;
    }

    @GetMapping(value = "base/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseEntity> findByName(@PathVariable String name){
        BaseEntity serviceByName = service.findByName(name);



        return new ResponseEntity<>(serviceByName, HttpStatus.OK);
    }

    @GetMapping("baseID/{id}")
    public ResponseEntity<BaseEntity> findByName(@PathVariable Integer id){
        BaseEntity serviceByName = service.findByID(id);

        return new ResponseEntity<>(serviceByName, HttpStatus.OK);
    }
}
