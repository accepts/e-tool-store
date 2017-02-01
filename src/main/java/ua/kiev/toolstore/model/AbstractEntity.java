package ua.kiev.toolstore.model;

import javax.persistence.*;


@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = true)



    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return this.id.equals(that.getId());
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}