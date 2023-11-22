package com.capstone.epam.evotingsystem.web;


import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.service.PublisherService;
import com.capstone.epam.evotingsystem.service.UserService;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumService;
import com.capstone.epam.evotingsystem.service.voting.survey.SociologicalSurveyService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publishers")
@CrossOrigin("*")
public class PublisherRestController {
    private PublisherService publisherService;
    private UserService userService;
    private SociologicalSurveyService sociologicalSurveyService;
    private ReferendumService referendumService;


    public PublisherRestController(PublisherService publisherService, UserService userService, SociologicalSurveyService sociologicalSurveyService, ReferendumService referendumService) {
        this.publisherService = publisherService;
        this.userService = userService;
        this.sociologicalSurveyService = sociologicalSurveyService;
        this.referendumService = referendumService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Commission Member')")
    public Page<PublisherDTO> searchPublishers(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return publisherService.findPublishersByName(keyword, page, size);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Commission Member')")
    public List<PublisherDTO> findAllPublishers() {
        return publisherService.fetchPublishers();
    }


    @DeleteMapping("/{publisherId}")
    @PreAuthorize("hasAuthority('Commission Member')")
    public void deletePublisher(@PathVariable Long publisherId) {
        publisherService.removePublisher(publisherId);
    }


    @PostMapping("/register")
    public PublisherDTO savePublisher(@RequestBody PublisherDTO publisherDTO) {
        User user = userService.loadUserByUsername(publisherDTO.getUser().getUsername());
        if (user != null) throw new RuntimeException("Username already exists!");
        return publisherService.createPublisher(publisherDTO);
    }

    @PutMapping("/{publisherId}")
    @PreAuthorize("hasAuthority('Publisher')")
    public PublisherDTO updatePublisher(@RequestBody PublisherDTO publisherDTO, @PathVariable Long publisherId) {
        publisherDTO.setPublisherId(publisherId);
        return publisherService.updatePublisher(publisherDTO);
    }

    @GetMapping("/{publisherId}/sociological-surveys")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Voter')")
    public Page<SociologicalSurveyDTO> getSociologicalSurveysByPublisherId(@PathVariable Long publisherId,
                                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return sociologicalSurveyService.fetchSociologicalSurveysForPublisher(publisherId, page, size);
    }

    @GetMapping("/{publisherId}/referendums")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Voter')")
    public Page<ReferendumDTO> getReferendumsByPublisherId(@PathVariable Long publisherId,
                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "0") int size) {
        return referendumService.fetchReferendumsForPublisher(publisherId, page, size);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('Publisher', 'Voter')")
    public PublisherDTO loadPublisherByUsername(@RequestParam(name = "username", defaultValue = "") String username) {
        return publisherService.loadPublisherByUsername(username);
    }


    @GetMapping("/{publisherId}/publications")
    @PreAuthorize("hasAnyAuthority('Commission Member','Publisher')")
    public Page<Object> getPublicationsByPublisherId(@PathVariable Long publisherId,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        return publisherService.fetchPublicationsByPublisherId(publisherId, page, size);
    }


}
