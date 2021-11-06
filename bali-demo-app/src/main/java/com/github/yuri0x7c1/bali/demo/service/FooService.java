package com.github.yuri0x7c1.bali.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import com.github.yuri0x7c1.bali.demo.repository.FooRepository;
import com.github.yuri0x7c1.bali.demo.base.service.FooBaseService;

@Slf4j
@Service
public class FooService extends FooBaseService {

	protected FooRepository fooRepository;

	@Autowired
	public FooService(FooRepository fooRepository) {
		super(fooRepository);
		this.fooRepository = fooRepository;
	}
}
