package com.usktea.plainold.repositories;

import com.usktea.plainold.models.reply.Parent;
import com.usktea.plainold.models.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, JpaSpecificationExecutor {
    List<Reply> findAllByParent(Parent parent);
}
