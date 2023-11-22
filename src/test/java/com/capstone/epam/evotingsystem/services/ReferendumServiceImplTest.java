package com.capstone.epam.evotingsystem.services;

import com.capstone.epam.evotingsystem.dao.voting.referendum.ReferendumDao;
import com.capstone.epam.evotingsystem.dto.voting.referendum.ReferendumDTO;
import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.mapper.voting.referendum.ReferendumMapper;
import com.capstone.epam.evotingsystem.service.impl.voting.referendum.ReferendumServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReferendumServiceImplTest {
    @Mock
    private ReferendumDao referendumDao;

    @Mock
    private ReferendumMapper referendumMapper;

    @InjectMocks
    private ReferendumServiceImpl referendumService;

    @Test
    public void testLoadReferendumById() {
        Long referendumId = 1L;
        ReferendumDTO expectedReferendumDTO = new ReferendumDTO();

        Referendum expectedReferendum = new Referendum();

        Mockito.when(referendumDao.findReferendumByReferendumId(referendumId)).thenReturn(expectedReferendum);

        Mockito.when(referendumMapper.fromReferendum(expectedReferendum)).thenReturn(expectedReferendumDTO);

        ReferendumDTO loadedReferendum = referendumService.loadReferendumById(referendumId);

        assertNotNull(loadedReferendum);

        verify(referendumDao, times(1)).findReferendumByReferendumId(referendumId);

        verify(referendumMapper, times(1)).fromReferendum(expectedReferendum);
    }
}
