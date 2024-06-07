package sumcoda.webide.entry.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sumcoda.webide.workspace.domain.Workspace;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    private String content;

    @Column(nullable = false)
    private Boolean isDirectory;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Entry parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Entry> children;

    // 연관관게 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // Entry N <-> 1 Workspace
    // 양방향 연관관계 편의 메서드
    public void assignWorkspace(Workspace workspace) {
        if (this.workspace != null) {
            this.workspace.getEntries().remove(this);
        }
        this.workspace = workspace;

        if (!workspace.getEntries().contains(this)) {
            workspace.addEntry(this);
        }
    }

}
