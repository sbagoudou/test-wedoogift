import { Component, forwardRef, OnDestroy, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { CalculatorResult } from '../../models/calculator-result';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { WedoogiftApiService } from '../../services/http/wedoogift-api.service';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CalculatorComponent),
      multi: true
    }
  ]
})
export class CalculatorComponent implements ControlValueAccessor, OnInit, OnDestroy {

  private destroy: Subject<void> = new Subject<void>();

  value = 20;
  dirty = true;

  isMin = false;
  isMax = false;

  apiResults?: CalculatorResult;

  disabled = false;

  onChange: any = () => {};
  onTouched: any = () => {};

  constructor(private wedoogiftApiService: WedoogiftApiService) {}

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
    this.destroy.unsubscribe();
  }

  onValidateClick(): void {
    this.dirty = false;
    this.onChange(null);

    this.wedoogiftApiService
      .getCombination$(5, this.value)
      .pipe(takeUntil(this.destroy))
      .subscribe((res: CalculatorResult) => {

        if (!res.equal && !res.floor && res.ceil && res.ceil.value > this.value) {
          // Case #1: the desired amount is not possible and it is lower than the possible amounts
          this.patchAmount(res.ceil.value);
        } else if (!res.equal && !res.ceil && res.floor && res.floor.value < this.value) {
          // Case #2: the desired amount is not possible and it is higher than the possible amounts
          this.patchAmount(res.floor.value);
        } else {
          // Case #3 : there are a lower and higher amount available
          this.apiResults = res;

          if (this.apiResults.equal) {
            // Case #4 : the desired amount is possible
            this.onChange(this.value);
          }
        }
      }, (error: any) => {
        console.error(error);
        this.dirty = true;
      });
  }

  patchAmount(value: number): void {
    this.value = value;
    this.onValidateClick();
  }

  computeNextValue(sign: string): void {
    let nextValue = 0;

    switch (sign) {
      case 'plus':
        nextValue = this.value + 1;
        break;
      case 'minus':
        nextValue = this.value - 1;
        break;
    }

    this.wedoogiftApiService
      .getCombination$(5, nextValue)
      .pipe(takeUntil(this.destroy))
      .subscribe((res: CalculatorResult) => {
        if (res) {
          this.isMin = res.ceil && !res.floor;
          this.isMax = res.floor && !res.ceil;
          switch (sign) {
            case 'plus':
              if (res.ceil) {
                this.patchAmount(res.ceil.value);
              }
              break;
            case 'minus':
              if (res.floor) {
                this.patchAmount(res.floor.value);
              }
              break;
          }
        }
      });
  }

  writeValue(value: number): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

}
