import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  title = 'calculator-client';

  form: FormGroup = this.createForm();

  ngOnInit(): void {
    this.form.valueChanges.subscribe(value => console.log(value));
  }

  createForm(): FormGroup {
    return new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      amount: new FormControl(null, Validators.required)
    });
  }
}
