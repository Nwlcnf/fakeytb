package com.fakeytb.fakeytb;

import com.fakeytb.fakeytb.Model.Role;
import com.fakeytb.fakeytb.Repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class LoadDatabase implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public LoadDatabase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role("Admin", "Administrateur du système");
        Role role2 = new Role("User", "Utilisateur standard");

        roleRepository.save(role1);
        roleRepository.save(role2);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
