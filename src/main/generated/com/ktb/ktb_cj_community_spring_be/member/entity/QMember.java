package com.ktb.ktb_cj_community_spring_be.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 317522032L;

    public static final QMember member = new QMember("member1");

    public final com.ktb.ktb_cj_community_spring_be.global.entity.QBaseEntity _super = new com.ktb.ktb_cj_community_spring_be.global.entity.QBaseEntity(this);

    public final ListPath<com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike, com.ktb.ktb_cj_community_spring_be.comment.entity.QCommentLike> commentLikes = this.<com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike, com.ktb.ktb_cj_community_spring_be.comment.entity.QCommentLike>createList("commentLikes", com.ktb.ktb_cj_community_spring_be.comment.entity.CommentLike.class, com.ktb.ktb_cj_community_spring_be.comment.entity.QCommentLike.class, PathInits.DIRECT2);

    public final ListPath<com.ktb.ktb_cj_community_spring_be.comment.entity.Comment, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment> comments = this.<com.ktb.ktb_cj_community_spring_be.comment.entity.Comment, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment>createList("comments", com.ktb.ktb_cj_community_spring_be.comment.entity.Comment.class, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.ktb.ktb_cj_community_spring_be.post.entity.PostLike, com.ktb.ktb_cj_community_spring_be.post.entity.QPostLike> postLikes = this.<com.ktb.ktb_cj_community_spring_be.post.entity.PostLike, com.ktb.ktb_cj_community_spring_be.post.entity.QPostLike>createList("postLikes", com.ktb.ktb_cj_community_spring_be.post.entity.PostLike.class, com.ktb.ktb_cj_community_spring_be.post.entity.QPostLike.class, PathInits.DIRECT2);

    public final ListPath<com.ktb.ktb_cj_community_spring_be.post.entity.Post, com.ktb.ktb_cj_community_spring_be.post.entity.QPost> posts = this.<com.ktb.ktb_cj_community_spring_be.post.entity.Post, com.ktb.ktb_cj_community_spring_be.post.entity.QPost>createList("posts", com.ktb.ktb_cj_community_spring_be.post.entity.Post.class, com.ktb.ktb_cj_community_spring_be.post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final EnumPath<com.ktb.ktb_cj_community_spring_be.member.entity.type.MemberRoleType> roleType = createEnum("roleType", com.ktb.ktb_cj_community_spring_be.member.entity.type.MemberRoleType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

