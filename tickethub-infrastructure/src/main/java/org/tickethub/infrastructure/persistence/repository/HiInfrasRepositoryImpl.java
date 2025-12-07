package org.tickethub.infrastructure.persistence.repository;

import org.springframework.stereotype.Service;
import org.tickethub.repository.HiDomainRepository;

@Service
public class HiInfrasRepositoryImpl implements HiDomainRepository {
    @Override
    public String sayHi(String who) {
        return "Hi from Infrastructure, " + who + "!";
    }
}
