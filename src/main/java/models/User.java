/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user_table", schema = "public")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="uid")
    public Long uid;
    @Column(name="name")
    public String name;
    @Column(name="email",length=50, nullable=false, unique=true)
    public String email;
    @Column(name="password", length=50, nullable = false)
    public String password;
    @Column(name="profile_pic")
    public String profile_pic;
    @Column(name="mobile")
    public String mobile;
    @Column(name="address")
    public String address;
    @Column(name="about")
    public String about;
    @Column(name = "isAdmin")
    public int isAdmin;
    
    public User() {}

	public User(String name, String email, String password, String profile_pic, String mobile, String address, String about) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.profile_pic = profile_pic;
		this.mobile = mobile;
		this.address = address;
		this.about = about;
		this.isAdmin = 0;
	}
}
