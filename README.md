# Java 8 and functional programming

## API and Optional

1. Modify your `Cache` from previous task and return `Optional#empty` instead of `null` if 
element is missing. 
1. We use `Optional` for return values, is it a good idea to use it as incoming parameters?
Write down your thoughts.

## Method references and Streams

1. Modify your `Order` class, create method which would return `boolean` depends on the value of
numeric field (which was added in previous task).
1. Modify your filter algorithm, use new method and `Streams API`.

## Default methods

1. Create new interface called `OrderFactory`. Interface should provide methods for 
creation of `Orders` with different `OrderStatuses`. For each status create `default` method.
What method should provide implementation of this interface? Write your own implementation.
