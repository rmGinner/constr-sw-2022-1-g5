package br.rmginner.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.Content;
import br.rmginner.repositories.ContentsRepository;
import br.rmginner.services.ContentService;
import br.rmginner.utils.BusinessException;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsRepository repository;

    @Override
    public ContentDto create(ContentDto dto) {
        final var contentEntity = this.repository.save(this.convertDtoToEntity(dto));
        dto.setId(contentEntity.getId());
        dto.setName(contentEntity.getName());
        dto.setType(contentEntity.getType());
        dto.setLink(contentEntity.getLink());
        return dto;
    }

    @Override
    public List<ContentDto> getBy(String contentId) {
        final var example = Example.of(Content.builder().id(contentId).build());

        return repository.findAll(example)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContentDto> getContentById(String id) {
        return this.repository.findById(id).map(this::convertEntityToDto);
    }

    @Override
    public void deleteById(String id) {
        final var contentOpt = this.repository.findById(id);

        if (contentOpt.isPresent()) {
            final var updatedContent = contentOpt.get();
            updatedContent.setEnabled(false);
            this.repository.save(updatedContent);
        }
    }

    @Override
    public ContentDto patchUpdate(ContentDto contentDto) {
        final var foundContentOpt = this.repository.findOne(Example.of(Content.builder().isEnabled(true).id(contentDto.getId()).build()));

        if (foundContentOpt.isEmpty()) {
            throw new BusinessException("Nenhum conteúdo encontrado para este ID.");
        }
        var contentUpdated = foundContentOpt.get();
        
        this.updateEntityFromDto(contentDto, contentUpdated);
        
        contentUpdated = this.repository.save(contentUpdated);
        
        return convertEntityToDto(contentUpdated);
    }

    private void updateEntityFromDto(ContentDto contentDto, Content content) {
    	content.setName(StringUtils.isEmpty(contentDto.getName()) ? content.getName() : contentDto.getName());
    	content.setType(contentDto.getType() != null ? content.getType() : contentDto.getType());
    	content.setLink(StringUtils.isEmpty(contentDto.getLink()) ? content.getLink() : contentDto.getLink());
    }
    
    private ContentDto convertEntityToDto(Content content) {
        return ContentDto.builder()
                .id(content.getId())
                .name(content.getName())
                .type(content.getType())
                .link(content.getLink())
                .build();
    }
    
    private Content convertDtoToEntity(ContentDto dto) {
        return Content.builder()
                .id(dto.getId())
                .name(dto.getName())
                .type(dto.getType())
                .link(dto.getLink())
                .build();
    }
}
