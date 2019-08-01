package io.swagger.api;

import com.thalesgroup.datastorage.datadisney.api.WaitingTimeApi;
import com.thalesgroup.datastorage.datadisney.api.model.Attraction;
import io.swagger.api.Repository.AttractionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
public class WaitingTimeApiController implements WaitingTimeApi {

    private AttractionRepository repository;
    private final ModelMapper modelMapper;

    public WaitingTimeApiController(ModelMapper modelMapper, AttractionRepository repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Attraction> getCurrentLine(@Valid String attId, @Valid String parkId) {
        // Fetch entity from API
//        Optional<io.swagger.api.dao.Attraction> optionalAttraction = repository.findById(attId);

        final Optional<io.swagger.api.dao.Attraction> optionalAttraction = repository.findAttractionByIdAndAndParkId(attId, parkId);

        if (optionalAttraction.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not foudn for id " + attId);
        } else {
            final io.swagger.api.dao.Attraction dao = optionalAttraction.get();

            // Convert dao -> dto
            ModelMapper modelMapper = new ModelMapper();
            final Attraction dto = modelMapper.map(dao, Attraction.class);

            // Return dto
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }
}
