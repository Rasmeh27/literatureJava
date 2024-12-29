package com.literature.literature.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class GutendexResponse {
    private int count;
    private String next;
    private String previous;
    private List<Book> results;

    @JsonIgnoreProperties(ignoreUnknown = true)  // Ignorará campos desconocidos
    public static class Book {
        private int id;
        private String title;
        private List<Author> authors;
        private List<String> languages;
        private List<String> translators;  // Campo que faltaba
        private List<String> subjects;     // Otros campos comunes
        private List<String> bookshelves;
        @JsonProperty("copyright")
        private boolean hasCopyright;
        @JsonProperty("media_type")
        private String mediaType;
        @JsonProperty("download_count")
        private int downloadCount;
        private Map<String, Map<String, String>> formats;  // Para los enlaces de descarga

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Author {
            private String name;
            @JsonProperty("birth_year")
            private Integer birthYear;
            @JsonProperty("death_year")
            private Integer deathYear;

            // Getters y setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getBirthYear() {
                return birthYear;
            }

            public void setBirthYear(Integer birthYear) {
                this.birthYear = birthYear;
            }

            public Integer getDeathYear() {
                return deathYear;
            }

            public void setDeathYear(Integer deathYear) {
                this.deathYear = deathYear;
            }
        }

        // Getters y setters para Book
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Author> getAuthors() {
            return authors;
        }

        public void setAuthors(List<Author> authors) {
            this.authors = authors;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public int getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(int downloadCount) {
            this.downloadCount = downloadCount;
        }

        // Getters y setters para los nuevos campos
        public List<String> getTranslators() {
            return translators;
        }

        public void setTranslators(List<String> translators) {
            this.translators = translators;
        }

        public List<String> getSubjects() {
            return subjects;
        }

        public void setSubjects(List<String> subjects) {
            this.subjects = subjects;
        }

        public List<String> getBookshelves() {
            return bookshelves;
        }

        public void setBookshelves(List<String> bookshelves) {
            this.bookshelves = bookshelves;
        }

        public boolean isHasCopyright() {
            return hasCopyright;
        }

        public void setHasCopyright(boolean hasCopyright) {
            this.hasCopyright = hasCopyright;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public Map<String, Map<String, String>> getFormats() {
            return formats;
        }

        public void setFormats(Map<String, Map<String, String>> formats) {
            this.formats = formats;
        }
    }

    // Getters y setters para GutendexResponse
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }
}