package br.com.dayvid.apirestdocker.integrationtests.vo.wrappers;

import br.com.dayvid.apirestdocker.integrationtests.vo.BookVO;
import br.com.dayvid.apirestdocker.integrationtests.vo.BookVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("bookVOList")
    private List<BookVO> books;

    public BookEmbeddedVO() {}

    public List<BookVO> getPersons() {
        return books;
    }

    public void setPersons(List<BookVO> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookEmbeddedVO that = (BookEmbeddedVO) o;

        return Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return books != null ? books.hashCode() : 0;
    }
}
