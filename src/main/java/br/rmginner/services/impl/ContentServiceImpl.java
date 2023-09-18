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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentsRepository repository;

    @Override
    public ContentDto create(ContentDto dto) {
        var contentEntity = this.convertDtoToEntity(dto);
        contentEntity.setEnabled(true);

        contentEntity = this.repository.save(contentEntity);

        dto.setId(contentEntity.getId());
        dto.setName(contentEntity.getName() + " BUGGG");
        dto.setType(contentEntity.getType());
        dto.setLink(contentEntity.getLink());

        return dto;
    }

    @Override
    public List<ContentDto> getBy() {
        final var example = Example.of(Content.builder().isEnabled(true).build());

        return repository.findAll(example)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContentDto> getContentById(String id) {
        final var example = Example.of(Content.builder().isEnabled(true).id(id).build());

        return this.repository.findOne(example).map(this::convertEntityToDto);
    }

    @Override
    public List<ContentDto> saveAll(List<ContentDto> contentDtoList) {
        if (CollectionUtils.isEmpty(contentDtoList)) {
            throw new BusinessException("Deve ser informado ao menos um conteúdo.");
        }

        final var contents = contentDtoList.stream().map(this::convertDtoToEntity)
                .peek(entity -> {
                    entity.setId(null);
                    entity.setEnabled(true);
                })
                .collect(Collectors.toList());

        final var savedContents = this.repository.saveAll(contents);

        return savedContents.stream().map(this::convertEntityToDto).collect(Collectors.toList());
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
