package com.capstone.epam.evotingsystem.web;


import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.service.voting.referendum.ReferendumService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("referendums")
@CrossOrigin("*")
public class ReferendumRestController {

    private ReferendumService referendumService;


    public ReferendumRestController(ReferendumService referendumService) {
        this.referendumService = referendumService;
    }


    @GetMapping
    public Page<ReferendumDTO> searchReferendums(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return referendumService.findReferendumByTitle(keyword, page, size);
    }


    @DeleteMapping("/{referendumId}")
    @PreAuthorize("hasAnyAuthority('Commission Member', 'Publisher')")
    public void deleteReferendum(@PathVariable Long referendumId) {
        referendumService.removeReferendum(referendumId);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('Publisher')")
    public ReferendumDTO saveReferendum(@RequestBody ReferendumDTO referendumDTO) {
        return referendumService.createReferendum(referendumDTO);
    }


    @PutMapping("/{referendumId}")
    @PreAuthorize("hasAuthority('Publisher')")
    public ReferendumDTO updateReferendumId(@RequestBody ReferendumDTO referendumDTO, @PathVariable Long referendumId) {
        referendumDTO.setReferendumId(referendumId);
        return referendumService.updateReferendum(referendumDTO);
    }

    @PostMapping("/{referendumId}/subscribe-on/voters/{voterId}")
    @PreAuthorize("hasAuthority('Voter')")
    public void subscribeVoterOnReferendum(@PathVariable Long referendumId, @PathVariable Long voterId) {
        referendumService.assignVoterToReferendum(referendumId, voterId);
    }

    @GetMapping("/{referendumId}/referendum")
    public ReferendumDTO getReferendum(@PathVariable Long referendumId) {
        return referendumService.loadReferendumById(referendumId);
    }


    @PutMapping("/{referendumId}/referendum-question/vote")
    @PreAuthorize("hasAuthority('Voter')")
    public ResponseEntity<String> incrementOptionValue(
            @PathVariable Long referendumId,
            @RequestParam String option) {
        try {
            referendumService.incrementOptionValue(referendumId, option);
            return ResponseEntity.ok("Value incremented successfully");
        } catch (Exception e) {
            return ResponseEntity.ok("Value incremented successfully");
        }
    }

}
