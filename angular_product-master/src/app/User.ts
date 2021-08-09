export class User {
  bio: string;
  dob: string;
  id: number;
  name: number;
  password: string;
  profilePicture: string;
  score: number;
  username: string;

  constructor(data: any) {
    this.bio = data.bio;
    this.dob = data.dob;
    this.id = data.id;
    this.name = data.name;
    this.password = data.password;
    this.score = data.score;
    this.username = data.username;
  }
}
