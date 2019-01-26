package demo.spring.aspect.service.impl;

import org.springframework.stereotype.Service;

import demo.spring.aspect.service.IAspectService;

@Service
public class AspectServiceImpl implements IAspectService {

    @Override
    public long addFromTo(long start, long end) {
        long result = 0L;
        for (long i = start; i < end; i++) {
            result += i;
        }
        return result;
    }

    @Override
    public long divide(long a, long b) {
        return a / b;
    }

}
