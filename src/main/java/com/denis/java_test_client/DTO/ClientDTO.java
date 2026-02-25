package com.denis.java_test_client.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    @JsonProperty("id")
    private Integer client_id;  //идентификатор клиента
    @JsonProperty("name")
    private String name;        //имя клиента
    @JsonProperty("last_name")
    private String last_name;   //фамилия клиента

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(client_id, clientDTO.client_id) && Objects.equals(name, clientDTO.name) && Objects.equals(last_name, clientDTO.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id, name, last_name);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "client_id=" + client_id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
