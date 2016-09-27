package com.arangodb.spark.vpack

import org.apache.spark.sql.Row
import org.apache.spark.sql.types._

import com.arangodb.velocypack.VPackBuilder
import com.arangodb.velocypack.VPackSlice
import com.arangodb.velocypack.ValueType
import com.arangodb.spark.TestEntity
import scala.math.BigDecimal
import org.apache.hadoop.mapred.MapTask

private[spark] object VPackUtils {

  def rowToVPack(row: Row): VPackSlice = {
    val builder = new VPackBuilder()
    builder.add(ValueType.OBJECT)
    row.schema.fields.zipWithIndex.foreach { addField(_, row, builder) }
    builder.close()
    builder.slice()
  }

  private def addField(field: (StructField, Int), row: Row, builder: VPackBuilder): Unit = {
    val name = field._1.name
    val i = field._2
    field._1.dataType match {
      case BooleanType    => builder.add(name, java.lang.Boolean.valueOf(row.getBoolean(i)))
      case DoubleType     => builder.add(name, java.lang.Double.valueOf(row.getDouble(i)))
      case FloatType      => builder.add(name, java.lang.Float.valueOf(row.getFloat(i)))
      case LongType       => builder.add(name, java.lang.Long.valueOf(row.getLong(i)))
      case IntegerType    => builder.add(name, java.lang.Integer.valueOf(row.getInt(i)))
      case ShortType      => builder.add(name, java.lang.Short.valueOf(row.getShort(i)))
      case StringType     => builder.add(name, java.lang.String.valueOf(row.getString(i)))
      case DateType       => builder.add(name, row.getDate(i))
      case TimestampType  => builder.add(name, row.getTimestamp(i))
      case t: DecimalType => builder.add(name, row.getDecimal(i))
      case t: MapType => {
        builder.add(name, ValueType.OBJECT)
        row.getMap[String, Any](i).foreach { case (name, value) => addValue(name, value, builder) }
        builder.close()
      }
      case t: ArrayType => {
        builder.add(name, ValueType.ARRAY)
        row.getSeq(i).foreach { value => addValue(null, value, builder) }
        builder.close()
      }
      case NullType           => builder.add(name, ValueType.NULL)
      case struct: StructType => builder.add(name, rowToVPack(row.getStruct(i)))
      case _                  => // TODO
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