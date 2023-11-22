package com.capstone.epam.evotingsystem.web;


import com.capstone.epam.evotingsystem.dto.VoterDTO;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.dto.voting.survey.SociologicalSurveyDTO;
import com.capstone.epam.evotingsystem.entity.User;
import com.capstone.epam.evotingsystem.service.UserService;
import com.capstone.epam.evotingsystem.service.VoterService;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumService;
import com.capstone.epam.evotingsystem.service.voting.survey.SociologicalSurveyService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voters")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VoterRestController {

    private VoterService voterService;
    private UserService userService;
    private SociologicalSurveyService sociologicalSurveyService;
    private ReferendumService referendumService;

    public VoterRestController(VoterService voterService, UserService userService, SociologicalSurveyService sociologicalSurveyService, ReferendumService referendumService) {
        this.voterService = voterService;
        this.userService = userService;
        this.sociologicalSurveyService = sociologicalSurveyService;
        this.referendumService = referendumService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Commission Member')")
    public Page<VoterDTO> searchVoters(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "5") int size) {
        return voterService.loadVotersByName(keyword, page, size);
    }

    @DeleteMapping("/{voterId}")
    @PreAuthorize("hasAuthority('Commission Member')")
    public void deleteVoter(@PathVariable Long voterId) {
        voterService.removeVoter(voterId);
    }


    @PostMapping("/register")
    public VoterDTO saveVoter(@RequestBody VoterDTO voterDTO) {
        User user = userService.loadUserByUsername(voterDTO.getUser().getUsername());
        if (user != null) throw new RuntimeException("Username already exists!");
        return voterService.createVoter(voterDTO);
    }


    @PutMapping("/{voterId}")
    @PreAuthorize("hasAuthority('Voter')")
    public VoterDTO updateVoter(@RequestBody VoterDTO voterDTO, @PathVariable Long voterId) {
        voterDTO.setVoterId(voterId);
        return voterService.updateVoter(voterDTO);
    }


    @GetMapping("/{voterId}/sociological-surveys")
    @PreAuthorize("hasAuthority('Voter')")
    public Page<SociologicalSurveyDTO> getSociologicalSurveysByVoterId(@PathVariable Long voterId,
                                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        return sociologicalSurveyService.fetchSociologicalSurveysForVoter(voterId, page, size);
    }

    @GetMapping("/{voterId}/other-sociological-surveys")
    @PreAuthorize("hasAuthority('Voter')")
    public Page<SociologicalSurveyDTO> getNonSubscribedOnSociologicalSurveysByVoterId(@PathVariable Long voterId,
                                                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(name = "size", defaultValue = "5") int size) {
        return sociologicalSurveyService.fetchSociologicalSurveysForNonSubscribedOnVoter(voterId, page, size);
    }

    @GetMapping("/{voterId}/referendums")
    @PreAuthorize("hasAuthority('Voter')")
    public Page<ReferendumDTO> getReferendumsByVoterId(@PathVariable Long voterId,
                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        return referendumService.fetchReferendumsForVoter(voterId, page, size);
    }

    @GetMapping("/{voterId}/other-referendums")
    @PreAuthorize("hasAuthority('Voter')")
    public Page<ReferendumDTO> getNonSubscribedOnReferendumsByVoterId(@PathVariable Long voterId,
                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        return referendumService.fetchReferendumsForNonSubscribedOnVoter(voterId, page, size);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Publisher', 'Voter')")
    public VoterDTO loadVoterByUsername(@RequestParam(name = "username", defaultValue = "") String username){
        return voterService.loadVoterByUsername(username);
    }



    @GetMapping("/{voterId}/subscriptions")
    @PreAuthorize("hasAnyAuthority('Commission Member','Voter')")
    public Page<Object> getSubscriptionsByVoterId(@PathVariable Long voterId,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size) {
        return voterService.fetchSubscriptionsByVoterId(voterId, page, size);
    }
}
