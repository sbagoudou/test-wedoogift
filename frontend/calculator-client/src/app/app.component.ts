import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = 'calculator-client';

  onValueChange(amount: number): void {
    console.log(`Amount change event received: ${amount} €`);
  }
}
