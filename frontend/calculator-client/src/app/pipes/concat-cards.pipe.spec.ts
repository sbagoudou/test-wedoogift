import { ConcatCardsPipe } from './concat-cards.pipe';

describe('ConcatCardsPipe', () => {
  it('create an instance', () => {
    const pipe = new ConcatCardsPipe();
    expect(pipe).toBeTruthy();
  });

  it('should concat values correctly', () => {
    const pipe = new ConcatCardsPipe();
    expect(pipe.transform([1, 23, 456])).toEqual('480 € (1 + 23 + 456)');
  });
});
