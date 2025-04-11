package com.nazarov.projects.blog.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nazarov.projects.blog.dtos.CreateTagDto;
import com.nazarov.projects.blog.events.TagDeletedEvent;
import com.nazarov.projects.blog.exceptions.NullIdException;
import com.nazarov.projects.blog.exceptions.ResourceNotFoundException;
import com.nazarov.projects.blog.exceptions.TagExistsException;
import com.nazarov.projects.blog.models.Tag;
import com.nazarov.projects.blog.models.mappers.TagEntityMapper;
import com.nazarov.projects.blog.repositories.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class TagServiceImplTest {

  @Mock
  private TagRepository tagRepository;

  @Mock
  private TagEntityMapper tagEntityMapper;

  @Mock
  private ApplicationEventPublisher publisher;

  @InjectMocks
  private TagServiceImpl tagService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createTag_ShouldSaveTag_WhenTagDoesNotExist() {
    CreateTagDto createTagDto = new CreateTagDto("Tech");
    Tag tag = new Tag("Tech");

    when(tagEntityMapper.toEntity(createTagDto)).thenReturn(tag);
    when(tagRepository.save(tag)).thenReturn(tag);

    Tag result = tagService.createTag(createTagDto);

    assertNotNull(result);
    assertEquals("Tech", result.getName());
    verify(tagRepository, times(1)).save(tag);
  }

  @Test
  void createTag_ShouldThrowException_WhenTagExists() {
    CreateTagDto createTagDto = new CreateTagDto("Tech");
    Tag tag = new Tag("Tech");

    when(tagEntityMapper.toEntity(createTagDto)).thenReturn(tag);
    when(tagRepository.findByName("Tech")).thenReturn(Optional.of(tag));

    assertThrows(TagExistsException.class, () -> tagService.createTag(createTagDto));
  }

  @Test
  void getTag_ShouldReturnTag_WhenIdIsValid() {
    Long tagId = 1L;
    Tag tag = new Tag("Tech");
    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    Tag result = tagService.getTag(tagId);

    assertNotNull(result);
    assertEquals("Tech", result.getName());
    verify(tagRepository, times(1)).findById(tagId);
  }

  @Test
  void getTag_ShouldThrowException_WhenIdIsNull() {
    assertThrows(NullIdException.class, () -> tagService.getTag(null));
  }

  @Test
  void getTag_ShouldThrowException_WhenTagNotFound() {
    Long tagId = 1L;
    when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> tagService.getTag(tagId));
  }

  @Test
  void deleteTag_ShouldDeleteTag_WhenIdIsValid() {
    Long tagId = 1L;
    Tag tag = new Tag("Tech");
    when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

    tagService.deleteTag(tagId);

    verify(tagRepository, times(1)).deleteById(tagId);
    verify(publisher, times(1)).publishEvent(any(TagDeletedEvent.class));
  }

  @Test
  void resolveTags_ShouldReturnExistingAndNewTags() {
    List<String> tagNames = List.of("Tech", "Java");
    Tag existingTag = new Tag("Tech");
    Tag newTag = new Tag("Java");

    when(tagRepository.findByName("Tech")).thenReturn(Optional.of(existingTag));
    when(tagRepository.findByName("Java")).thenReturn(Optional.empty());
    when(tagRepository.save(new Tag("Java"))).thenReturn(newTag);

    Set<Tag> result = tagService.resolveTags(tagNames);

    assertEquals(2, result.size());
    assertTrue(result.contains(existingTag));
    assertTrue(result.contains(newTag));
  }

  @Test
  void getTags_ShouldReturnAllTags() {
    List<Tag> tags = List.of(new Tag("Tech"), new Tag("Java"));
    when(tagRepository.findAll()).thenReturn(tags);

    List<Tag> result = tagService.getTags();

    assertEquals(2, result.size());
    assertEquals("Tech", result.get(0).getName());
    assertEquals("Java", result.get(1).getName());
  }

  @Test
  void getTags_ShouldReturnPagedTags() {
    Pageable pageable = Pageable.ofSize(10);
    List<Tag> tags = List.of(new Tag("Tech"), new Tag("Java"));
    Page<Tag> page = new PageImpl<>(tags);

    when(tagRepository.findAll(pageable)).thenReturn(page);

    Page<Tag> result = tagService.getTags(pageable);

    assertEquals(2, result.getContent().size());
    assertEquals("Tech", result.getContent().get(0).getName());
    assertEquals("Java", result.getContent().get(1).getName());
  }

  @Test
  void getTagByName_ShouldReturnTag_WhenNameExists() {
    String tagName = "Tech";
    Tag tag = new Tag(tagName);
    when(tagRepository.findByName(tagName)).thenReturn(Optional.of(tag));

    Optional<Tag> result = tagService.getTagByName(tagName);

    assertTrue(result.isPresent());
    assertEquals(tag, result.get());
  }

  @Test
  void getTagByName_ShouldReturnEmpty_WhenNameDoesNotExist() {
    String tagName = "NonExistentTag";
    when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

    Optional<Tag> result = tagService.getTagByName(tagName);

    assertFalse(result.isPresent());
  }
}