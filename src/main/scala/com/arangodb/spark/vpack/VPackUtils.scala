/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
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
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 *
 * author Mark - mark at arangodb.com
 */

package com.arangodb.spark.vpack

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{
  ArrayType,
  BooleanType,
  DataType,
  DateType,
  DecimalType,
  DoubleType,
  FloatType,
  IntegerType,
  LongType,
  MapType,
  NullType,
  ShortType,
  StringType,
  StructField,
  StructType,
  TimestampType
}
import com.arangodb.velocypack.VPackBuilder
import com.arangodb.velocypack.VPackSlice
import com.arangodb.velocypack.ValueType

private[spark] object VPackUtils {

  def rowToVPack(row: Row): VPackSlice = {
    val builder = new VPackBuilder()
    if (row == null) {
      builder.add(ValueType.NULL)
    } else {
      builder.add(ValueType.OBJECT)
      row.schema.fields.zipWithIndex.foreach { addField(_, row, builder) }
      builder.close()
    }
    builder.slice()
  }

  private def addField(field: (StructField, Int), row: Row, builder: VPackBuilder): Unit = {
    val name = field._1.name
    val index = field._2
    if (row.isNullAt(index)) {
      builder.add(name, ValueType.NULL)
    } else {
      field._1.dataType match {
        case BooleanType    => builder.add(name, java.lang.Boolean.valueOf(row.getBoolean(index)))
        case DoubleType     => builder.add(name, java.lang.Double.valueOf(row.getDouble(index)))
        case FloatType      => builder.add(name, java.lang.Float.valueOf(row.getFloat(index)))
        case LongType       => builder.add(name, java.lang.Long.valueOf(row.getLong(index)))
        case IntegerType    => builder.add(name, java.lang.Integer.valueOf(row.getInt(index)))
        case ShortType      => builder.add(name, java.lang.Short.valueOf(row.getShort(index)))
        case StringType     => builder.add(name, java.lang.String.valueOf(row.getString(index)));
        case DateType       => builder.add(name, row.getDate(index))
        case TimestampType  => builder.add(name, row.getTimestamp(index))
        case t: DecimalType => builder.add(name, row.getDecimal(index))
        case t: MapType => {
          builder.add(name, ValueType.OBJECT)
          row.getMap[String, Any](index).foreach { case (name, value) => addValue(name, value, builder) }
          builder.close()
        }
        case t: ArrayType => {
          builder.add(name, ValueType.ARRAY)
          addValues(row, index, builder, t.elementType)
          builder.close()
        }
        case NullType           => builder.add(name, ValueType.NULL)
        case struct: StructType => builder.add(name, rowToVPack(row.getStruct(index)))
        case _                  => // TODO
      }
    }
  }

  private def addValues(row: Row, index: Int, builder: VPackBuilder, itemType: DataType): Unit = {
    itemType match {
      case BooleanType =>
        row.getSeq[Boolean](index).foreach { value =>
          addValue(null, value, builder)
        }
      case DoubleType =>
        row.getSeq[Double](index).foreach { value =>
          addValue(null, value, builder)
        }
      case FloatType =>
        row.getSeq[Float](index).foreach { value =>
          addValue(null, value, builder)
        }
      case LongType =>
        row.getSeq[Long](index).foreach { value =>
          addValue(null, value, builder)
        }
      case IntegerType =>
        row.getSeq[Int](index).foreach { value =>
          addValue(null, value, builder)
        }
      case ShortType =>
        row.getSeq[Short](index).foreach { value =>
          addValue(null, value, builder)
        }
      case StringType =>
        row.getSeq[String](index).foreach { value =>
          addValue(null, value, builder)
        }
      case DateType =>
        row.getSeq[java.sql.Date](index).foreach { value =>
          addValue(null, value, builder)
        }
      case TimestampType =>
        row.getSeq[java.sql.Timestamp](index).foreach { value =>
          addValue(null, value, builder)
        }
      case s: StructType => {
        row.getSeq[Row](index).foreach { value =>
          builder.add(null, rowToVPack(value))
        }
      }
      case t: MapType   => // TODO
      case t: ArrayType => // TODO
      case _            => // TODO
    }
  }

  private def addValue(name: String, value: Any, builder: VPackBuilder): Unit = {
    value match {
      case value: Boolean            => builder.add(name, java.lang.Boolean.valueOf(value))
      case value: Double             => builder.add(name, java.lang.Double.valueOf(value))
      case value: Float              => builder.add(name, java.lang.Float.valueOf(value))
      case value: Long               => builder.add(name, java.lang.Long.valueOf(value))
      case value: Int                => builder.add(name, java.lang.Integer.valueOf(value))
      case value: Short              => builder.add(name, java.lang.Short.valueOf(value))
      case value: String             => builder.add(name, java.lang.String.valueOf(value))
      case value: java.sql.Date      => builder.add(name, value)
      case value: java.sql.Timestamp => builder.add(name, value)
      case _                         => // TODO
    }
  }

}
