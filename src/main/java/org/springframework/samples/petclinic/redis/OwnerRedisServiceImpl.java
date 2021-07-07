package org.springframework.samples.petclinic.redis;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.redis.OwnerRedis;
import org.springframework.samples.petclinic.redis.OwnerRedisRepository;
import org.springframework.samples.petclinic.redis.OwnerRedisService;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("OwnerServiceImpl")

public class OwnerRedisServiceImpl implements OwnerRedisService  {
	
	@Autowired
	private OwnerRedisRepository ownerRedisRepository;
	
	@Transactional
	//@Cacheable(value = "ownerpetlist")
	//
	public OwnerRedis get(String lastName) {
		OwnerRedis ownerObj = null;
		Collection<OwnerRedis> ownerResponse = ownerRedisRepository.findByLastName(lastName);
		if (!ownerResponse.isEmpty()) {
			//student = ownerResponse.//.get();
		} else {
			throw new RuntimeException("Record not found");
		}
		return ownerObj;
	}

	@Override
	public OwnerRedis get(OwnerRedis ownerredis) {
		// TODO Auto-generated method stub
		return null;
	}

}
