SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: comments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    content text NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    author bigint,
    parent bigint,
    post bigint
);


--
-- Name: comments_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.comments_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: communities; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.communities (
    id bigint NOT NULL,
    area_tag character varying(255) NOT NULL,
    city character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    created_by bigint
);


--
-- Name: communities_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.communities_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: community_members; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.community_members (
    id bigint NOT NULL,
    joined_at timestamp(6) without time zone NOT NULL,
    role character varying(255) NOT NULL,
    user_id bigint,
    community_id bigint
);


--
-- Name: community_members_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.community_members_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: event_rvsps; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.event_rvsps (
    id bigint NOT NULL,
    rvsp_at timestamp(6) without time zone,
    status character varying(255),
    event bigint,
    user_id bigint
);


--
-- Name: event_rvsps_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.event_rvsps_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: events; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.events (
    id bigint NOT NULL,
    capacity integer NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    description character varying(255) NOT NULL,
    event_time timestamp(6) without time zone NOT NULL,
    lat double precision,
    lng double precision,
    location character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    community bigint,
    organizer bigint
);


--
-- Name: events_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.events_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: market_place_listings; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.market_place_listings (
    id bigint NOT NULL,
    active boolean NOT NULL,
    category character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    description text NOT NULL,
    price numeric(38,2) NOT NULL,
    title character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    community bigint,
    seller bigint
);


--
-- Name: market_place_listings_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.market_place_listings_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: media_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.media_files (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    entity_type character varying(255) NOT NULL,
    mime_type character varying(255) NOT NULL,
    size bigint NOT NULL,
    storageurl character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    uploaded_by bigint
);


--
-- Name: media_files_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.media_files_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: notifications; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notifications (
    id bigint NOT NULL,
    channel character varying(255) NOT NULL,
    payload character varying(255) NOT NULL,
    read boolean NOT NULL,
    send_at timestamp(6) without time zone NOT NULL,
    type character varying(255) NOT NULL,
    recipient bigint
);


--
-- Name: notifications_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.notifications_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: pets; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pets (
    id bigint NOT NULL,
    bio character varying(255) NOT NULL,
    birth_date date NOT NULL,
    breed character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    name character varying(255) NOT NULL,
    photourl character varying(255) NOT NULL,
    species character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    owner bigint NOT NULL
);


--
-- Name: pets_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pets_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: posts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.posts (
    id bigint NOT NULL,
    category character varying(255) NOT NULL,
    content text NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    title character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    view_count bigint NOT NULL,
    author bigint,
    community bigint
);


--
-- Name: posts_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.posts_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: reactions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reactions (
    id bigint NOT NULL,
    type character varying(255) NOT NULL,
    post bigint,
    user_id bigint
);


--
-- Name: reactions_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.reactions_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: refresh_token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.refresh_token (
    id bigint NOT NULL,
    expires_at timestamp(6) with time zone NOT NULL,
    token character varying(255) NOT NULL,
    user_id bigint
);


--
-- Name: refresh_token_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.refresh_token ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.refresh_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    avatarurl character varying(255),
    created_at timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    username character varying(255) NOT NULL
);


--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- Name: communities communities_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.communities
    ADD CONSTRAINT communities_pkey PRIMARY KEY (id);


--
-- Name: community_members community_members_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.community_members
    ADD CONSTRAINT community_members_pkey PRIMARY KEY (id);


--
-- Name: event_rvsps event_rvsps_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_rvsps
    ADD CONSTRAINT event_rvsps_pkey PRIMARY KEY (id);


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: market_place_listings market_place_listings_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.market_place_listings
    ADD CONSTRAINT market_place_listings_pkey PRIMARY KEY (id);


--
-- Name: media_files media_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.media_files
    ADD CONSTRAINT media_files_pkey PRIMARY KEY (id);


--
-- Name: notifications notifications_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (id);


--
-- Name: pets pets_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT pets_pkey PRIMARY KEY (id);


--
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- Name: reactions reactions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reactions
    ADD CONSTRAINT reactions_pkey PRIMARY KEY (id);


--
-- Name: refresh_token refresh_token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);


--
-- Name: communities uk3vr2q12p5v4unsi7025edy28c; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.communities
    ADD CONSTRAINT uk3vr2q12p5v4unsi7025edy28c UNIQUE (name);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: refresh_token ukf95ixxe7pa48ryn1awmh2evt7; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT ukf95ixxe7pa48ryn1awmh2evt7 UNIQUE (user_id);


--
-- Name: media_files uklfksgeql1rp3v84ooji34ae0p; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.media_files
    ADD CONSTRAINT uklfksgeql1rp3v84ooji34ae0p UNIQUE (storageurl);


--
-- Name: refresh_token ukr4k4edos30bx9neoq81mdvwph; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT ukr4k4edos30bx9neoq81mdvwph UNIQUE (token);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: events fk742kk6fr6voa3wlayumegifr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fk742kk6fr6voa3wlayumegifr FOREIGN KEY (community) REFERENCES public.communities(id);


--
-- Name: event_rvsps fk7o9836y8pcqoe616thk5wa1jd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_rvsps
    ADD CONSTRAINT fk7o9836y8pcqoe616thk5wa1jd FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: posts fk9k4xbcojf8g0etikyd7j5p1b3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fk9k4xbcojf8g0etikyd7j5p1b3 FOREIGN KEY (author) REFERENCES public.users(id);


--
-- Name: posts fkax8rin4jdamet41k0mmnt3f1b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT fkax8rin4jdamet41k0mmnt3f1b FOREIGN KEY (community) REFERENCES public.communities(id);


--
-- Name: market_place_listings fkcebeovf4p0svd3s2hdca7haj2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.market_place_listings
    ADD CONSTRAINT fkcebeovf4p0svd3s2hdca7haj2 FOREIGN KEY (community) REFERENCES public.communities(id);


--
-- Name: events fkea3he60gq9c5hd11gxvpvmss; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT fkea3he60gq9c5hd11gxvpvmss FOREIGN KEY (organizer) REFERENCES public.users(id);


--
-- Name: communities fkhu58kqkbabws9cbbptc6n6534; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.communities
    ADD CONSTRAINT fkhu58kqkbabws9cbbptc6n6534 FOREIGN KEY (created_by) REFERENCES public.users(id);


--
-- Name: notifications fkj5vhs0q088pvcanfj3up73pbg; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notifications
    ADD CONSTRAINT fkj5vhs0q088pvcanfj3up73pbg FOREIGN KEY (recipient) REFERENCES public.users(id);


--
-- Name: market_place_listings fkjc1udms7i1mtjthxllkeymbr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.market_place_listings
    ADD CONSTRAINT fkjc1udms7i1mtjthxllkeymbr FOREIGN KEY (seller) REFERENCES public.users(id);


--
-- Name: refresh_token fkjtx87i0jvq2svedphegvdwcuy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.refresh_token
    ADD CONSTRAINT fkjtx87i0jvq2svedphegvdwcuy FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: comments fkmcsi74v5msx3nsrk6mnwe455d; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkmcsi74v5msx3nsrk6mnwe455d FOREIGN KEY (post) REFERENCES public.posts(id);


--
-- Name: event_rvsps fkmdwe7fuuupiq4gkws1r20qhbu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.event_rvsps
    ADD CONSTRAINT fkmdwe7fuuupiq4gkws1r20qhbu FOREIGN KEY (event) REFERENCES public.events(id);


--
-- Name: community_members fkme7k1stbnwi6cpmm8a6sgcikn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.community_members
    ADD CONSTRAINT fkme7k1stbnwi6cpmm8a6sgcikn FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: comments fkmre4xj0uxvqyo1ejku9ijy7r0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkmre4xj0uxvqyo1ejku9ijy7r0 FOREIGN KEY (parent) REFERENCES public.users(id);


--
-- Name: pets fknpppq4oy3lp88fhsynav8mxp3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pets
    ADD CONSTRAINT fknpppq4oy3lp88fhsynav8mxp3 FOREIGN KEY (owner) REFERENCES public.users(id);


--
-- Name: media_files fkoqqolx98kdn5gra10be95a6tf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.media_files
    ADD CONSTRAINT fkoqqolx98kdn5gra10be95a6tf FOREIGN KEY (uploaded_by) REFERENCES public.users(id);


--
-- Name: comments fkp6ilf8rosuwl497khjofovggk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkp6ilf8rosuwl497khjofovggk FOREIGN KEY (author) REFERENCES public.users(id);


--
-- Name: reactions fkqmewaibcp5bxtlqxc2cawhuln; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reactions
    ADD CONSTRAINT fkqmewaibcp5bxtlqxc2cawhuln FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: community_members fkqn9g17tqcwnoy41o2am9fnlep; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.community_members
    ADD CONSTRAINT fkqn9g17tqcwnoy41o2am9fnlep FOREIGN KEY (community_id) REFERENCES public.communities(id);


--
-- Name: reactions fkxv0gakxesbnxwsr6c81aqwt9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reactions
    ADD CONSTRAINT fkxv0gakxesbnxwsr6c81aqwt9 FOREIGN KEY (post) REFERENCES public.posts(id);
