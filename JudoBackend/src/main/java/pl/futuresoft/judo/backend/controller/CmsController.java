package pl.futuresoft.judo.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.futuresoft.judo.backend.dto.PageStaticDto;
import pl.futuresoft.judo.backend.service.CmsService;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
public class CmsController {
	
	@Autowired
	CmsService cmsService;
	
	@GetMapping("/page")
	public ResponseEntity<List<PageStaticDto>> getStrony()
	{
		List<PageStaticDto> strony = cmsService.getStronyWyswietlane();
		return new ResponseEntity<List<PageStaticDto>>(strony, HttpStatus.OK);

	}

	@GetMapping("/page/{pageId}")
	public ResponseEntity<PageStaticDto> getPage(
			@PathVariable("pageId") Integer pageId) throws AccessDeniedException
	{
		PageStaticDto page = cmsService.getPage(pageId);
		return new ResponseEntity<PageStaticDto>(page, HttpStatus.OK);

	}
	

	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	@GetMapping("/backoffice/page")
	public ResponseEntity<List<PageStaticDto>> getStronyBackoffice()
	{
		List<PageStaticDto> strony = cmsService.getStronyBackoffice();
		return new ResponseEntity<List<PageStaticDto>>(strony, HttpStatus.OK);

	}

	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	@GetMapping("/backoffice/page/{pageId}")
	public ResponseEntity<PageStaticDto> getPageBackoffice(
			@PathVariable("pageId") Integer pageId) throws AccessDeniedException
	{
		PageStaticDto page = cmsService.getPageBackoffice(pageId);
		return new ResponseEntity<PageStaticDto>(page, HttpStatus.OK);

	}


	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	@PutMapping("/backoffice/page/{pageId}")
	public ResponseEntity<String> editPageBackoffice(
			@PathVariable("pageId") Integer pageId, @RequestBody PageStaticDto page) throws AccessDeniedException
	{
		cmsService.editPageBackoffice(page, pageId);
		return new ResponseEntity<String>((String)null, HttpStatus.NO_CONTENT);

	}

	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	@PostMapping("/backoffice/page")
	public ResponseEntity<PageStaticDto> addPageBackoffice(
		 @RequestBody PageStaticDto page) throws AccessDeniedException
	{
		PageStaticDto pageNowa = cmsService.addPageBackoffice(page);
		return new ResponseEntity<PageStaticDto>(pageNowa, HttpStatus.OK);

	}


	@PreAuthorize("hasAuthority('ROLE_o') || hasAuthority('ROLE_a')")
	@DeleteMapping("/backoffice/page/{pageId}")
	public ResponseEntity<String> editPageBackoffice(
			@PathVariable("pageId") Integer pageId) throws AccessDeniedException
	{
		cmsService.deletePageBackoffice(pageId);
		return new ResponseEntity<String>((String)null, HttpStatus.NO_CONTENT);

	}
}
