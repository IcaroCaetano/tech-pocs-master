package com.rbleggi.logisticpricing

import com.rbleggi.logisticpricing.TransportType.{Boat, Rail, Truck}

import scala.math.BigDecimal.RoundingMode.HALF_UP
import scala.util.Random

enum TransportType:
  case Truck, Rail, Boat

case class FreightInfo(volume: Double, size: Double, distance: Double, transportType: TransportType)

trait PricingStrategy:
  def calculate(info: FreightInfo): Double

class TruckPricingStrategy extends PricingStrategy:
  def calculate(info: FreightInfo): Double =
    val base = 1.5
    val price = (info.volume * 0.8 + info.size * 0.5 + info.distance * base) * TruckPricingStrategy.getDynamicFactor()
    BigDecimal(price).setScale(2, HALF_UP).toDouble

object TruckPricingStrategy:
  def getDynamicFactor(): Double =
    1.0 + Random.between(-0.1, 0.1) 

class RailPricingStrategy extends PricingStrategy:
  def calculate(info: FreightInfo): Double =
    val base = 1.2
    val price = (info.volume * 0.6 + info.size * 0.4 + info.distance * base) * RailPricingStrategy.getDynamicFactor()
    BigDecimal(price).setScale(2, HALF_UP).toDouble

object RailPricingStrategy:
  def getDynamicFactor(): Double =
    1.0 + Random.between(-0.15, 0.15)

class BoatPricingStrategy extends PricingStrategy:
  def calculate(info: FreightInfo): Double =
    val base = 1.0
    val price = (info.volume * 0.4 + info.size * 0.3 + info.distance * base) * BoatPricingStrategy.getDynamicFactor()
    BigDecimal(price).setScale(2, HALF_UP).toDouble

object BoatPricingStrategy:
  def getDynamicFactor(): Double =
    1.0 + Random.between(-0.2, 0.2)

class FreightCalculator(strategy: PricingStrategy):
  def calculate(info: FreightInfo): Double = strategy.calculate(info)

object PricingStrategySelector:
  def forTransportType(t: TransportType): PricingStrategy = t match
    case Truck => TruckPricingStrategy()
    case Rail => RailPricingStrategy()
    case Boat => BoatPricingStrategy()

@main def runLogisticPricingApp(): Unit =
  val shipments = List(
    FreightInfo(10, 5, 100, Truck),
    FreightInfo(20, 8, 300, Rail),
    FreightInfo(15, 6, 500, Boat)
  )

  shipments.foreach { info =>
    val strategy = PricingStrategySelector.forTransportType(info.transportType)
    val calculator = FreightCalculator(strategy)
    val price = calculator.calculate(info)
    println(s"Freight using ${info.transportType} costs $$${price}")
  }
