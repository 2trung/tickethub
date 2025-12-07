package org.tickethub.service.impl;

import org.tickethub.repository.HiDomainRepository;
import org.tickethub.service.HiDomainService;
import org.springframework.stereotype.Service;

@Service
public class HiDomainServiceImpl implements HiDomainService {

    private final HiDomainRepository hiDomainRepository;

    public  HiDomainServiceImpl(HiDomainRepository hiDomainRepository) {
        this.hiDomainRepository = hiDomainRepository;
    }

    @Override
    public String sayHi(String who) {
        return hiDomainRepository.sayHi(who);
    }
}
