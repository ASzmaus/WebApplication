package pl.futuresoft.judo.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.dto.PageStaticDto;
import pl.futuresoft.judo.backend.entity.PageStatic;
import pl.futuresoft.judo.backend.repository.PageStaticRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CmsService {

	@Autowired
	PageStaticRepository pageStaticRepository;
	
	@Transactional 
	public PageStaticDto getPage(Integer pageId) throws AccessDeniedException
	{
		PageStatic page = pageStaticRepository.findById(pageId).get();
		if (!page.isDisplay())
			throw new AccessDeniedException("Page forbiden");
		PageStaticDto pageDto = new PageStaticDto();
		writeEntityToDto(page, pageDto);
		return pageDto;
	}
	
	public void writeEntityToDto(PageStatic p, PageStaticDto pageDto)
	{
		pageDto.setPageStaticId(p.getPageStaticId());
		pageDto.setContent(p.getContent());
		pageDto.setTitle(p.getTitle());
	}

	public void writeEntityToDtoBackoffice(PageStatic p, PageStaticDto pageDto)
	{
		pageDto.setPageStaticId(p.getPageStaticId());
		pageDto.setContent(p.getContent());
		pageDto.setTitle(p.getTitle());
		pageDto.setInnerName(p.getInnerName());
		pageDto.setDisplay(p.isDisplay());
		pageDto.setSequence(p.getSequence());
	}
	public void writeDtoToEntity(PageStaticDto p, PageStatic pageDto)
	{
		pageDto.setContent(p.getContent());
		pageDto.setTitle(p.getTitle());
		pageDto.setInnerName(p.getInnerName());
		pageDto.setDisplay(p.isDisplay());
		pageDto.setSequence(p.getSequence());
	}
	
	@Transactional
	public List<PageStaticDto> getStronyWyswietlane()
	{
		List<PageStatic> pagesDto = pageStaticRepository.findWyswietlane();
		return
				pagesDto.stream().map(p-> {
					PageStaticDto pageDto = new PageStaticDto();
					writeEntityToDto(p, pageDto);
			return pageDto;
		}).collect(Collectors.toList());
	}
	

	@Transactional
	public List<PageStaticDto> getStronyBackoffice()
	{
		Iterable<PageStatic> pagesDto = pageStaticRepository.findAll();
		return
				StreamSupport.stream(pagesDto.spliterator(),false).map(p-> {
					PageStaticDto pageDto = new PageStaticDto();
					writeEntityToDtoBackoffice(p, pageDto);
			return pageDto;
		}).collect(Collectors.toList());
	}
	

	@Transactional 
	public PageStaticDto getPageBackoffice(Integer pageId) throws AccessDeniedException
	{
		PageStatic page = pageStaticRepository.findById(pageId).get();
		PageStaticDto pageDto = new PageStaticDto();
		writeEntityToDtoBackoffice(page, pageDto);
		return pageDto;
	}
	
	@Transactional
	public PageStaticDto addPageBackoffice(PageStaticDto dto)
	{
		PageStatic db = new PageStatic();
		writeDtoToEntity(dto, db);
		pageStaticRepository.save(db);
		dto.setPageStaticId(db.getPageStaticId());
		return dto;
	}
	
	@Transactional
	public void editPageBackoffice(PageStaticDto dto, Integer pageStaticId)
	{
		PageStatic s = pageStaticRepository.findById(pageStaticId).get();
		writeDtoToEntity(dto, s);
		pageStaticRepository.save(s);
	}
	
	@Transactional
	public void deletePageBackoffice(Integer pageStaticId)
	{
		PageStatic s = pageStaticRepository.findById(pageStaticId).get();
		pageStaticRepository.delete(s);
	}
}
