package com.wemade.coupon.config;

import com.google.common.base.CaseFormat;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseCleaner {
  @PersistenceContext
  private EntityManager entityManager;

  private List<String> tableNames;

  @PostConstruct
  public void init() {
    tableNames = entityManager.getMetamodel().getEntities().stream()
            .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
            .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
            .collect(Collectors.toList());
  }

  @Transactional
  public void execute() {  // (2)
    entityManager.flush();
    entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate();

    for (String tableName : tableNames) {
      entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
    }

    entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate();
  }
}
