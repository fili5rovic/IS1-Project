package subsys;

import entities.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JPAManager {

    EntityManager em;

    public JPAManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IS1-PROJECT-SUBSYS1PU");
        em = emf.createEntityManager();
        if (em == null) {
            System.out.println("[ERROR] em is NULL");
        }
    }

    public List<Serializable> persistObject(Serializable k) {
        em.getTransaction().begin();
        em.persist(k);
        em.getTransaction().commit();
        return java.util.Collections.singletonList(k);
    }

    public List<Serializable> get(String className, String queryStr) {
        try {
            Class<?> entityClass = Class.forName("entities." + className);
            if (!Serializable.class.isAssignableFrom(entityClass)) {
                throw new IllegalArgumentException("Class " + className + " does not implement Serializable");
            }

            TypedQuery<?> query = em.createQuery(queryStr, entityClass);

            return query.getResultList().stream().map(obj -> (Serializable) obj).collect(Collectors.toList());
        } catch (ClassNotFoundException | IllegalArgumentException ex) {
            System.out.println("No class found");
            return Collections.emptyList();
        }

    }

    public List<Serializable> getAll(String className) {
        try {
            Class<?> entityClass = Class.forName("entities." + className);
            if (!Serializable.class.isAssignableFrom(entityClass)) {
                throw new IllegalArgumentException("Class " + className + " does not implement Serializable");
            }

            TypedQuery<?> query = em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);

            return query.getResultList().stream().map(obj -> (Serializable) obj).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            System.out.println("No class found");
            return Collections.emptyList();
        }
    }

    public List<Serializable> update(String className, int id, HashMap<String, List<String>> map) {
        List<Serializable> result = new ArrayList<>();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Class<?> entityClass = Class.forName("entities." + className);

            Object entity = em.find(entityClass, id);
            if (entity == null) {
                return result;
            }

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                String value = entry.getValue().get(0);

                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                if (fieldType == String.class) {
                    field.set(entity, value);
                } else if (fieldType == int.class || fieldType == Integer.class) {
                    field.set(entity, Integer.valueOf(value));
                } else if (fieldType == long.class || fieldType == Long.class) {
                    field.set(entity, Long.valueOf(value));
                } else if (fieldType == double.class || fieldType == Double.class) {
                    field.set(entity, Double.valueOf(value));
                } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                    field.set(entity, Boolean.valueOf(value));
                } else if(fieldType == Mesto.class) {
                    Mesto m = em.find(Mesto.class, Integer.valueOf(value));
                    field.set(entity, m);
                }else if (isEntity(fieldType)) {
                    // pk is always int
                    Object ref = em.find(fieldType, Integer.valueOf(value));
                    field.set(entity, ref);
                } else {
                    field.set(entity, value);
                }

            }

            em.merge(entity);
            transaction.commit();

            result.add((Serializable) entity);

        } catch (ClassNotFoundException e) {
            Logger.getLogger(JPAManager.class.getName()).log(Level.SEVERE, "Class not found: " + className, e);
            transaction.rollback();
        } catch (NoSuchFieldException e) {
            System.out.println("No such field exists");
            transaction.rollback();
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
            Logger.getLogger(JPAManager.class.getName()).log(Level.SEVERE, "An error occurred while updating the entity", e);
            transaction.rollback();
        }

        return result;
    }

    public boolean delete(String className, int id) {
        try {
            Class<?> entityClass = Class.forName("entities." + className);

            Object entity = em.find(entityClass, id);
            if (entity == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
            return true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found");
        }

        return false;
    }

    private boolean isEntity(Class<?> clazz) {
        return clazz.getPackage().getName().equals("entities");
    }
}
