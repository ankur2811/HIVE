import { Component } from '@angular/core';
import { UserActivityService } from 'src/app/user-activity.service';

@Component({
  selector: 'app-uploader',
  templateUrl: './uploader.component.html',
  styleUrls: ['./uploader.component.scss'],
})
export class UploaderComponent {
  isHovering: boolean;

  files: File[] = [];
  constructor(private activity: UserActivityService) {}
  ngOnInit() {}
  toggleHover(event: boolean) {
    this.isHovering = event;
  }

  onDrop(files: FileList) {
    for (let i = 0; i < files.length; i++) {
      this.files.push(files.item(i));
    }
  }
  showPreview(event) {
    this.onDrop(event.target.files);
  }
  close() {
    this.ngOnInit();
  }
}
