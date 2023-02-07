package com.usktea.plainold.applications.reply;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.ReplyNotExists;
import com.usktea.plainold.models.reply.Parent;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeleteReplyService {
    private final GetUserService getUserService;
    private final ReplyRepository replyRepository;

    public DeleteReplyService(GetUserService getUserService,
                              ReplyRepository replyRepository) {
        this.getUserService = getUserService;
        this.replyRepository = replyRepository;
    }

    public Long delete(Username username, Long id) {
        Users user = getUserService.find(username);

        Reply reply = replyRepository.findById(id)
                .orElseThrow(ReplyNotExists::new);

        reply.delete(user.username());

        if (reply.isFirstReply()) {
            deleteAllChildren(reply.id());
        }

        return reply.id();
    }

    private void deleteAllChildren(Long id) {
        List<Reply> replies = replyRepository.findAllByParent(new Parent(id));

        replies.stream().forEach(Reply::delete);
    }
}
