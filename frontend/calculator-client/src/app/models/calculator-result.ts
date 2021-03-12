import { CalculatorComponentValue } from './calculator-component-value';

export interface CalculatorResult {
  equal?: CalculatorComponentValue;
  floor: CalculatorComponentValue;
  ceil: CalculatorComponentValue;
}
