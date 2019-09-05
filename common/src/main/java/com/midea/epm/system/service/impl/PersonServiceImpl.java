package com.midea.epm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.epm.system.entity.Person;
import com.midea.epm.system.mapper.PersonMapper;
import com.midea.epm.system.service.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl  extends ServiceImpl<PersonMapper, Person> implements PersonService {

}
