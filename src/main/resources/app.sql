CREATE VIEW video_details AS 
    SELECT 
        v.id as 'v_id', v.content_type, v.url, v.status, v.author_id,
        a.*,
        d.id as 'details_id', d.title, d.description, d.video_id,
        tag.video_youtube_id, tag.tags 
    FROM video v 
        LEFT JOIN users a on a.id = v.author_id
        LEFT JOIN video_youtube d ON v.id = d.video_id
        LEFT JOIN video_youtube_tags tag ON tag.video_youtube_id = d.id;