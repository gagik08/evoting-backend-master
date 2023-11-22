package com.capstone.epam.evotingsystem.mapper;

import com.capstone.epam.evotingsystem.dto.PublisherDTO;
import com.capstone.epam.evotingsystem.entity.Publisher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    @Autowired
    private UserMapper userMapper;

    public PublisherDTO fromPublisher(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        BeanUtils.copyProperties(publisher, publisherDTO);
        publisherDTO.setPublisherId(publisher.getPublisherId());
        publisherDTO.setPublicName(publisher.getPublicName());
        publisherDTO.setFounder(publisher.getFounder());
        publisherDTO.setUser(userMapper.fromUser(publisher.getUser()));
        return publisherDTO;
    }


    public Publisher fromPublisherDTO(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        BeanUtils.copyProperties(publisherDTO, publisher);
        return publisher;
    }
}
