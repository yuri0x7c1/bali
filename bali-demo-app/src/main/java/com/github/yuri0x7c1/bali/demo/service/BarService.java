package com.github.yuri0x7c1.bali.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import com.github.yuri0x7c1.bali.demo.repository.BarRepository;
import com.github.yuri0x7c1.bali.demo.base.service.BarBaseService;

@Slf4j
@Service
public class BarService extends BarBaseService {

	protected BarRepository barRepository;

	@Autowired
	public BarService(BarRepository barRepository) {
		super(barRepository);
		this.barRepository = barRepository;
	}
}
