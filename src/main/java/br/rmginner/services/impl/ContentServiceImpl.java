package br.rmginner.services.impl;

import br.rmginner.dtos.ContentDto;
import br.rmginner.entities.Content;
import br.rmginner.repositories.ContentsRepository;
import br.rmginner.services.ContentService;
import br.rmginner.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<ContentDto> saveAll(List<ContentDto> contentDtoList) {
        if (CollectionUtils.isEmpty(contentDtoList)) {
            throw new BusinessException("Deve ser informado ao menos um conteúdo.");
        }

        final var contents = contentDtoList.stream().map(this::convertDtoToEntity)
                .peek(entity -> entity.setId(null))
                .collect(Collectors.toList());

        final var savedContents = this.repository.saveAll(contents);

        return savedContents.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
