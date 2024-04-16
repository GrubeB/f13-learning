package pl.app.file.file.adapter.out;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.app.file.file.application.domain.File;
import pl.app.file.file.application.port.out.FileRepository;

import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public File save(File entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<File> findById(UUID fileId) {
        TypedQuery<File> jpqlQuery = entityManager.createQuery("SELECT f FROM File f WHERE f.id=:id", File.class);
        jpqlQuery.setParameter("id", fileId);
        return jpqlQuery.getResultStream().findFirst();
    }

    @Override
    public void delete(File entity) {
        entityManager.remove(entityManager.merge(entity));
    }
}
