package org.springframework.samples.petclinic.redis;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.redis.OwnerRedis;

import org.springframework.stereotype.Component;


@Component
public interface OwnerRedisService {
	
	public OwnerRedis get(OwnerRedis ownerredis);
	

}
