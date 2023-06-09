package com.will.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.will.domain.Contact;
import com.will.resources.util.URL;
import com.will.services.ContactService;

@RestController
@RequestMapping({ "/contacts", "/contacts/" })
public class ContactResource {

	@Autowired
	private ContactService service;

	@GetMapping
	public ResponseEntity<List<Contact>> findAllContacts() {
		List<Contact> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping({ "/{id}", "/{id}/" })
	public ResponseEntity<Contact> findContactById(@PathVariable String id) {
		Contact obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/subjectsearch")
	public ResponseEntity<List<Contact>> findContactBySubject(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Contact> list = service.findBySubject(text);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/textsearch")
	public ResponseEntity<List<Contact>> searchTextByDate(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Contact> list = service.searchTextInContactOrByDate(text, min, max);
		return ResponseEntity.ok().body(list);
	}

}