# endianness_benchmark

Quick benchmark for reading longs using Bytesbuffer and LongBuffer with different endianness.

```
Benchmark                          (byteOrder)   Mode  Cnt   Score   Error   Units
ReadLongBenchmark.readBytesBuffer           LE  thrpt   25   7,746 ± 0,305  ops/us
ReadLongBenchmark.readBytesBuffer           BE  thrpt   25   7,082 ± 0,101  ops/us
ReadLongBenchmark.readLongsBuffer           LE  thrpt   25  23,009 ± 0,228  ops/us
ReadLongBenchmark.readLongsBuffer           BE  thrpt   25   2,316 ± 0,019  ops/us
```
