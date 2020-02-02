package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.AclEntry;
import ru.otus.lib.domain.Book;
import ru.otus.lib.repository.AclEntryRepository;
import ru.otus.lib.repository.AclSidRepository;
import ru.otus.lib.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;
    private final AclEntryRepository aclEntryDao;
    private final AclSidRepository aclSidDao;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public Book createBook(Book book) {
        Book savedBook = bookDao.save(book);
        addAclEntries(book);
        return savedBook;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public Book updateBook(Book book) {
        return bookDao.save(book);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getBooksByParams(String filter) {
        return bookDao.findByParams(filter);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @PostAuthorize("hasPermission(returnObject.orElse(null), 'READ')")
    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookDao.findById(bookId);
    }
    
    private void addAclEntries(Book book) {
        Long adminSidId = aclSidDao.findBySid("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("ROLE_ADMIN is unavailable")).getId();
        Long userSidId = aclSidDao.findBySid("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER is unavailable")).getId();
        
        List<AclEntry> aclEntryList = Arrays.asList(
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(1)
                        .mask(BasePermission.READ.getMask())
                        .sid(adminSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build(),
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(2)
                        .mask(BasePermission.WRITE.getMask())
                        .sid(adminSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build(),
                AclEntry.builder()
                        .aclObjectIdentity(book.getId())
                        .aceOrder(3)
                        .mask(BasePermission.READ.getMask())
                        .sid(userSidId)
                        .granting(true)
                        .auditSuccess(true)
                        .auditFailure(true)
                        .build());
        
        aclEntryDao.saveAll(aclEntryList);
    }
}
