package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.UserWatchProgress;
import bestworkingconditions.biedaflix.server.model.request.UserWatchProgressRequest;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import bestworkingconditions.biedaflix.server.repository.UserWatchProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserWatchProgressController {

    private final UserWatchProgressRepository repository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserWatchProgressController(UserWatchProgressRepository repository, EpisodeRepository episodeRepository, UserRepository userRepository) {this.repository = repository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/users/{id}/progress")
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> addWatchProgress(
            @PathVariable String id,
            @Valid @RequestBody UserWatchProgressRequest request
            ){

        if(!episodeRepository.findById(request.getEpisodeId()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given name does not exist in database");
        }

        if(!userRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User of given name does not exist in database");
        }

        Optional<UserWatchProgress> userWatchProgress = repository.findByUserId(id);

        if(!userWatchProgress.isPresent()){
            UserWatchProgress newUserWatchProgress = new UserWatchProgress();
            newUserWatchProgress.setUserId(id);

            return ResponseEntity.ok(repository.save(newUserWatchProgress));
        }else {

            userWatchProgress.get()
                             .getProgress()
                             .put(request.getEpisodeId(), userWatchProgress.get().getProgress().getOrDefault(request.getEpisodeId(), request.getTime()));

            return ResponseEntity.ok(repository.save(userWatchProgress.get()));
        }
    }

    @GetMapping(value = "/users/{id}/progress")
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> getWatchProgress(@PathVariable String id){

        Optional<UserWatchProgress> userWatchProgressOptional = repository.findByUserId(id);

        if(userWatchProgressOptional.isPresent()){
            return ResponseEntity.ok(userWatchProgressOptional.get());
        }

        return ResponseEntity.ok(repository.save(new UserWatchProgress(id)));
    }
}
